import json
import requests
from django.http import JsonResponse


def json_extractor(data_json, field):
    json_str = json.dumps(data_json)
    data_loads = json.loads(json_str)
    return data_loads[field]


def pprint(json_text):
    print(json.dumps(json_text, indent=2))

print("""       

      **  LOGGING IN  **      
        
""")

# url_login = 'http://127.0.0.1:8000/api/v1/db/login/'
# data = {"username": "2017csb1110@iitrpr.ac.in", "password": "new_pass_123"}

url_login = 'http://127.0.0.1:8000/api/v1/db/login/'
data = {"username": "amritpal@gmail.com", "password": "new_pass_123"}
# print(data)
r = requests.post(url_login, json=data)
pprint(r.json())
print('status code is ', r.status_code)
token = ''
if(r.status_code == 200):
    token = json_extractor(r.json(), 'token')
headers = {'Authorization': f'Token {token}'}



print("""       

      **  Getting User Info  **      
        
""")
url_user_info = 'http://127.0.0.1:8000/api/v1/db/get_user_info/'
data = {"email": "amritpal@gmail.com", "password": "new_pass_123"}
r = requests.get(url_user_info,data=data, headers=headers)
print(r.status_code)
pprint(r.json())



print("""       

      **  LISTING ALL Users **
        
""")
url_list_student = 'http://127.0.0.1:8000/api/v1/db/list_students/'
r = requests.get(url_list_student, headers=headers)
pprint(r.json())

