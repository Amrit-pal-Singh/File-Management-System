from . import views
from django.conf.urls import url

urlpatterns = [
	url(r'^list/$', views.hierarchyListAPIView.as_view(), name='resthierarchy-list'),
    url(r'^add/$', views.Addhierarchy.as_view(), name='resthierarchy-add'),
    # url(r'^(?P<id>[\w-]+)/$', views.Showhierarchy.as_view(), name='resthierarchy-show'),
	url(r'^office/list/$', views.officeAllAPIView.as_view(), name='resthierarchy-list-office'),
	url(r'^office/add/$', views.AddOfficehierarchy.as_view(), name='resthierarchy-list-office'),
	url(r'^list/(?P<id>[\w-]+)/$', views.officeIdAPIView.as_view(), name='resthierarchy-office-id'),
] 