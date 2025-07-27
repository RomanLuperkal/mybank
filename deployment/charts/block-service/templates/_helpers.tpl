{{/*
Expand the name of the chart.
*/}}
{{- define "block-service.name" -}}
{{- .Chart.Name | trunc 63 | trimSuffix "-" -}}
{{- end }}

{{/*
Create a default fully qualified app name.
*/}}
{{- define "block-service.fullname" -}}
{{- printf "%s-%s" .Release.Name (include "block-service.name" .) | trunc 63 | trimSuffix "-" -}}
{{- end }}

{{/*
Common labels
*/}}
{{- define "block-service.labels" -}}
app.kubernetes.io/name: {{ include "block-service.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/version: {{ .Chart.AppVersion }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}
