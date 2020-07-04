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
data = {"username": "jai69@gmail.com","password": "new_pass_123"}
# data = {"username": "amritpal@gmail.com", "password": "new_pass_123"}
# print(data)
r = requests.post(url_login, json=data)
pprint(r.json())
print('status code is ', r.status_code)
token = ''
if(r.status_code == 200):
    token = json_extractor(r.json(), 'token')
headers = {'Authorization': f'Token {token}'}



print("""       

      **  Add new user  **      
        
""")
url_add_user = 'http://127.0.0.1:8000/api/v1/db/add_user/'
data = {"first_name": "Jai", "last_name": "Garg", "email": "jai69@gmail.com" ,"password": "new_pass_123"}
r = requests.post(url_add_user,data=data, headers=headers)
print(r.status_code)
pprint(r.json())



print("""       

      **  LISTING ALL Users **
        
""")
url_list_users = 'http://127.0.0.1:8000/api/v1/db/list_users/'
r = requests.get(url_list_users, headers=headers)
pprint(r.json())



path = ""

def set_path(x):
    path = x
    print(json.dumps(x))


def get_path():
    print(json.loads(path))



# dictionary = {'1': 'amrit'}
# set_path(dictionary)
# get_path()




