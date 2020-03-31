from django.contrib import admin
from django.urls import path

from django.conf.urls import include, url

urlpatterns =[ 
    path('admin/', admin.site.urls), 
	url(r'^account/', include(('userAccount.urls', 'userAccount'), namespace='userAccount')),
    url(r'^hierarchy/', include(('hierarchy.urls', 'hierarchy'), namespace='hierarchy')),
    url(r'^files/', include(('files.urls', 'files'), namespace='fiiles')),
    url(r'^api/v1/account/', include(('restaccount.urls', 'restprofile'), namespace='restaccount')),
    url(r'^api/v1/hierarchy/', include(('resthierarchy.urls', 'resthierarchy'), namespace='resthierarchy')),	
    url(r'^api/v1/files/', include(('restfiles.urls', 'restfiles'), namespace='restfiles')),	
    url(r'^userProfile/', include(('userProfile.urls', 'userProfile'), namespace='userProfile')),	
]