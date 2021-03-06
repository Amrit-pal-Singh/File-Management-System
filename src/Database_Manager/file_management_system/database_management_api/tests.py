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

# base_url = 'http://612f39a4af3b.ngrok.io/'
base_url = "http://55e5b6239f22.ngrok.io/"


url_login = base_url+'api/v1/db/login/'
data = {"username": "jai69@gmail.com","password": "new_pass_123"}
r = requests.post(url_login, json=data)

# this is the status code, if code <200 or  code >= 400 then it is a error
print(r.status_code)

# the data returned by the api, if the post request is correct then the same data will be returned.
pprint(r.json())
print('status code is ', r.status_code)

# in this I will store the token for future use.
# @Sheky remember to remove the code on logout.
token = ''
if(r.status_code == 200):
    token = json_extractor(r.json(), 'token')

# this header will be added to request everytime.
headers = {'Authorization': f'Token {token}'}



print("""

    ** List My Roles

""")
roles_url = base_url+'api/v1/db/roles'

# making the get request on above url
r = requests.get(roles_url, headers=headers)
print(r.status_code)
pprint(r.json())



print("""

    ** List All Roles Present

""")
roles_url = base_url+'api/v1/db/get_all_roles'

# making the get request on above url
r = requests.get(roles_url, headers=headers)
print(r.status_code)
pprint(r.json())



print("""

    ** Add Files

""")
roles_url = base_url + 'api/v1/db/add_file/'


# data is the name of the file, which will be typed by the user, after scan.
# qr is the qr scanned.
data = {
    "name": "Unsatisfactory Grade Submission",
    "qr": "6549"
}
r = requests.post(roles_url, json=data, headers=headers)
print(r.status_code)
pprint(r.json())

print("""

    ** List Files

""")
roles_url = base_url + 'api/v1/db/generated_files/'
r = requests.get(roles_url, headers=headers)
print(r.status_code)
pprint(r.json())




print("""

    ** Receive File

""")
roles_url = base_url + 'api/v1/db/receive_file/6549/'
data = {
    "email": "amrit@gmail.com",
    "role": "Director",
    "department": "",
    "qr": "6549"
}
r = requests.put(roles_url, json=data, headers=headers)
print(r.status_code)
pprint(r.json())




print("""

    ** PlanToSend

""")
roles_url = base_url + 'api/v1/db/plan_to_send/6549/'
data = {
    "role": "Instructor",
    "department": "CSE",
    "sender_email": "amrit@gmail.com",
    "qr": "6549"
}
r = requests.put(roles_url, json=data, headers=headers)
print(r.status_code)
pprint(r.json())




# print("""

#     ** ApproveFile

# """)
# roles_url = base_url + 'api/v1/db/approve_disapprove_file/6549/'
# data = {
#     "email": "amrit@gmail.com",
#     "role": "Director",
#     "department": "",
#     "approve": 1,
#     "qr": "6549"
# }
# r = requests.put(roles_url, json=data, headers=headers)
# print(r.status_code)
# pprint(r.json())




print("""

    ** view_my_plan_to_send_files

""")
roles_url = base_url + 'api/v1/db/view_my_plan_to_send_files/'
r = requests.get(roles_url, headers=headers)
print(r.status_code)
pprint(r.json())





print("""

    ** file_detail

""")
roles_url = base_url + 'api/v1/db/file_detail/6549/'
r = requests.get(roles_url, headers=headers)
print(r.status_code)
pprint(r.json())






print("""

    ** view_my_approved_disaaproved_files

""")
roles_url = base_url + 'api/v1/db/view_my_approved_disaaproved_files/'
r = requests.get(roles_url, headers=headers)
print(r.status_code)
pprint(r.json())

