import requests
import gspread
from oauth2client.service_account import ServiceAccountCredentials
import json

# Configura tu token de acceso y repositorio de GitHub
token = 'TU_TOKEN_DE_GITHUB'
repo_owner = 'DUEÑO_DEL_REPOSITORIO'
repo_name = 'NOMBRE_DEL_REPOSITORIO'

# URL de la API de GitHub
url = f'https://api.github.com/repos/{repo_owner}/{repo_name}/issues?state=open'

# Headers de autenticación
headers = {
    'Authorization': f'token {token}',
}

# Obtener los Issues del repositorio
response = requests.get(url, headers=headers)
issues = response.json()

# Filtrar Issues con puntos de historia (suponiendo que los puntos de historia están en las etiquetas)
issues_data = []
for issue in issues:
    if 'labels' in issue:
        points = None
        for label in issue['labels']:
            if 'story points' in label['name']:
                points = int(label['name'].split(':')[1].strip())
                break
        if points:
            issues_data.append({
                'title': issue['title'],
                'points': points,
                'created_at': issue['created_at'],
                'closed_at': issue.get('closed_at', None)
            })

# Autenticación y conexión a Google Sheets
scope = ["https://spreadsheets.google.com/feeds", 'https://www.googleapis.com/auth/spreadsheets', 'https://www.googleapis.com/auth/drive.file', "https://www.googleapis.com/auth/drive"]
creds = ServiceAccountCredentials.from_json_keyfile_name("tu_archivo_de_credenciales.json", scope)
client = gspread.authorize(creds)

# Abre el sheet (cambia el nombre de la hoja)
spreadsheet = client.open("Burndown Chart").sheet1

# Escribir los datos en el sheet
for issue in issues_data:
    spreadsheet.append_row([issue['title'], issue['points'], issue['created_at'], issue['closed_at']])

print("Datos actualizados correctamente en Google Sheets.")
