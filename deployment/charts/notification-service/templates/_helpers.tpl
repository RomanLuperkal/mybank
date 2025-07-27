{{/*
Expand the name of the chart.
*/}}
{{- define "notification-service.name" -}}
{{- .Chart.Name | trunc 63 | trimSuffix "-" -}}
{{- end }}

{{/*
Create a default fully qualified app name.
*/}}
{{- define "notification-service.fullname" -}}
{{- printf "%s-%s" .Release.Name (include "notification-service.name" .) | trunc 63 | trimSuffix "-" -}}
{{- end }}

{{/*
Common labels
*/}}
{{- define "notification-service.labels" -}}
app.kubernetes.io/name: {{ include "notification-service.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/version: {{ .Chart.AppVersion }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}
