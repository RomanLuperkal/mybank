{{/*
Expand the name of the chart.
*/}}
{{- define "exchange-generator.name" -}}
{{- .Chart.Name | trunc 63 | trimSuffix "-" -}}
{{- end }}

{{/*
Create a default fully qualified app name.
*/}}
{{- define "exchange-generator.fullname" -}}
{{- printf "%s-%s" .Release.Name (include "exchange-generator.name" .) | trunc 63 | trimSuffix "-" -}}
{{- end }}

{{/*
Common labels
*/}}
{{- define "exchange-generator.labels" -}}
app.kubernetes.io/name: {{ include "exchange-generator.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/version: {{ .Chart.AppVersion }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}
