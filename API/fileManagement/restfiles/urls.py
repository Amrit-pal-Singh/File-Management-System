
from restfiles import views
from django.conf.urls import url

urlpatterns = [
	url(r'^add/$', views.AddFile.as_view(), name='restfiles-list'),	
	url(r'^list/$', views.ListAll.as_view(), name='restfiles-list'),	
	url(r'^myFiles/$', views.ListMyFiles.as_view(), name='restfiles-list'),	
    url(r'^delete/(?P<qr>[\w-]+)/$', views.DeletePost.as_view(), name='delete'),
	url(r'^update/(?P<qr>[\w-]+)/$', views.PostUpdateAPIView.as_view(), name='edit'),
]