{{/*
Expand the name of the chart.
*/}}
{{- define "exchange-service.name" -}}
{{- .Chart.Name | trunc 63 | trimSuffix "-" -}}
{{- end }}

{{/*
Create a default fully qualified app name.
*/}}
{{- define "exchange-service.fullname" -}}
{{- printf "%s-%s" .Release.Name (include "exchange-service.name" .) | trunc 63 | trimSuffix "-" -}}
{{- end }}

{{/*
Common labels
*/}}
{{- define "exchange-service.labels" -}}
app.kubernetes.io/name: {{ include "exchange-service.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/version: {{ .Chart.AppVersion }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}
