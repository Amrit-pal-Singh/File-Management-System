from django.conf.urls import url
from . import views

urlpatterns = [
	url(r'^list', views.all_files, name='list'),    
	# url(r'^myFiles', views.all_files, name='list'),    
	url(r'^add', views.add_files, name='add'),    
    url(r'^update/(?P<pk>\w+)/$', views.update_file, name='update'),
    url(r'^delete/(?P<pk>\w+)/$', views.delete_file, name='delete'),
]