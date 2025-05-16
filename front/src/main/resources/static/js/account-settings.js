function changePassword(accountId) {
    const newPassword = document.getElementById('newPassword').value.trim();
    const error = document.getElementById('passwordError');
    if (!newPassword) {
        error.textContent = 'Пароль не может быть пустым.';
        return;
    }
    error.textContent = '';
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
    // Отправка запроса на изменение пароля

    fetch(`/account/${accountId}/change-password`, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json',
            [csrfHeader]: csrfToken },
        body: JSON.stringify({ password: newPassword })
    }).then(response => {
        console.log(response.status)
        if (response.ok) {
            alert('Пароль успешно изменён.');
        } else {
            alert('Ошибка при изменении пароля.');
        }
    });
}

// Сохранение профиля
function saveProfile(accountId) {
    event.preventDefault();
    const lastName = document.getElementById('lastName').value.trim();
    const firstName = document.getElementById('firstName').value.trim();
    const email = document.getElementById('email').value.trim();
    const birthDateStr = document.getElementById('birthDate').value;
    const error = document.getElementById('profileError');

    if (!lastName || !firstName || !email || !birthDateStr) {
        error.textContent = 'Заполните все поля.';
        return;
    }

    const birthDate = new Date(birthDateStr);
    const age = new Date().getFullYear() - birthDate.getFullYear();
    if (age < 18) {
        error.textContent = 'Вам должно быть больше 18 лет.';
        return;
    }

    error.textContent = '';
    // Отправка обновленных данных
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
    fetch(`/account/${accountId}/update-profile`, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json',
            [csrfHeader]: csrfToken },
        body: JSON.stringify({
            lastName: lastName,
            firstName: firstName,
            email: email,
            birthDate: birthDateStr
        })
    }).then(response => {
        if (response.ok) {
            alert('Профиль обновлён.');
        } else {
            response.json().then(body => {
                console.log(body.message);
                console.log("Весь body:", body); // <-- вот это важно
                const message = body?.message || "Произошла ошибка";
                alert(message);
            }).catch(() => {
                alert("Не удалось получить сообщение об ошибке");
            });
        }
    });
}

// Удаление счёта
function deleteWallet(accountId, walletId) {
    if (confirm('Вы уверены, что хотите удалить этот счёт?')) {
        fetch(`account/${accountId}/wallet/${walletId}`, { method: 'DELETE',
            headers: {
                [csrfHeader]: csrfToken
                    }
        })
            .then(response => {
                if (response.ok) {
                    location.reload(); // Перезагрузка страницы после удаления
                } else {
                    alert('Ошибка при удалении счёта.');
                }
            });
    }
}

function deleteAccount(accountId) {
    if (!accountId) {
        document.getElementById("deleteError").textContent = "Не указан ID аккаунта.";
        return;
    }

    if (!confirm("Вы уверены, что хотите удалить свой аккаунт? Это действие нельзя отменить.")) {
        return;
    }

    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

    fetch(`/account/${accountId}/delete-account`, {
        method: 'DELETE',
        headers: {
            [csrfHeader]: csrfToken
        }
    })
        .then(response => {
            if (response.ok) {
                alert("Аккаунт успешно удалён.");

                // Теперь делаем POST-запрос на logout
                return fetch('/logout', {
                    method: 'POST',
                    headers: {
                        [csrfHeader]: csrfToken,
                    },
                    credentials: 'include' // важно для кук
                });
            } else {
                return response.json().then(data => {
                    throw new Error(data.message || "Ошибка при удалении аккаунта");
                });
            }
        })
        .then(logoutResponse => {
            if (logoutResponse.redirected) {
                window.location.href = logoutResponse.url; // редирект Spring Security
            } else {
                window.location.href = '/'; // или '/login'
            }
        })
        .catch(error => {
            console.error("Ошибка:", error);
            document.getElementById("deleteError").textContent = error.message;
        });
}

function createWallet(accountId) {
    event.preventDefault();
    const currency = document.getElementById('currency').value;
    const error = document.getElementById('accountError');
    if (!currency) {
        error.textContent = 'Пожалуйста, выберите валюту.';
        return;
    }

    // Получаем CSRF-токен
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

    fetch(`/account/${accountId}/wallet`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify({ walletType: currency })
    })
        .then(response => {
            console.log("Статус:", response.status);
            console.log("Тип содержимого:", response.headers.get('Content-Type'));
            if (response.ok) {
                return response.json();
            } else {
                return response.text().then(text => {
                    throw new Error(text);
                });
            }
        })
        .then(wallet => {
            const accountList = document.getElementById('accountList');

            // Добавляем новый счёт в DOM
            const item = document.createElement('div');
            item.className = 'account-item';
            console.log(wallet.walletType);
            console.log(wallet.balance);
            item.innerHTML = `<span><strong>${wallet.walletType.toString()}: ${wallet.balance.toString()}</strong></span>`;
            accountList.appendChild(item);

            // Очистка формы
            document.getElementById('currency').value = '';
            error.textContent = '';
        })
        .catch(error => {
            console.error('Ошибка:', error);
            alert(error);
        });
}