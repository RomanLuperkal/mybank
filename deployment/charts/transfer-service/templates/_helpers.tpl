{{/*
Expand the name of the chart.
*/}}
{{- define "transfer-service.name" -}}
{{- .Chart.Name | trunc 63 | trimSuffix "-" -}}
{{- end }}

{{/*
Create a default fully qualified app name.
*/}}
{{- define "transfer-service.fullname" -}}
{{- printf "%s-%s" .Release.Name (include "transfer-service.name" .) | trunc 63 | trimSuffix "-" -}}
{{- end }}

{{/*
Common labels
*/}}
{{- define "transfer-service.labels" -}}
app.kubernetes.io/name: {{ include "transfer-service.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/version: {{ .Chart.AppVersion }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}
