{{/*
Expand the name of the chart.
*/}}
{{- define "cash-service.name" -}}
{{- .Chart.Name | trunc 63 | trimSuffix "-" -}}
{{- end }}

{{/*
Create a default fully qualified app name.
*/}}
{{- define "cash-service.fullname" -}}
{{- printf "%s-%s" .Release.Name (include "cash-service.name" .) | trunc 63 | trimSuffix "-" -}}
{{- end }}

{{/*
Common labels
*/}}
{{- define "cash-service.labels" -}}
app.kubernetes.io/name: {{ include "cash-service.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/version: {{ .Chart.AppVersion }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}
