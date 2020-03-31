from django.conf.urls import url
from . import views

urlpatterns = [
	url(r'^add', views.add, name='add'),
    url(r'^list', views.all_hierarchy, name='list'),
    # url(r'^(?P<pk>\d+)/$', views.show_post, name='post-show'),
    url(r'^office/(?P<pk>\d+)/$', views.list_office, name='office')
]