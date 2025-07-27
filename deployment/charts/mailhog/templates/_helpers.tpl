{{/*
Expand the name of the chart.
*/}}
{{- define "mailhog.name" -}}
{{- .Chart.Name | trunc 63 | trimSuffix "-" -}}
{{- end }}

{{/*
Create a default fully qualified app name.
*/}}
{{- define "mailhog.fullname" -}}
{{- printf "%s-%s" .Release.Name (include "mailhog.name" .) | trunc 63 | trimSuffix "-" -}}
{{- end }}

{{/*
Common labels
*/}}
{{- define "mailhog.labels" -}}
app.kubernetes.io/name: {{ include "mailhog.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}
