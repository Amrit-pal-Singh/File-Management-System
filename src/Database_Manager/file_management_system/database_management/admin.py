from django.contrib import admin

from .models import (
    Role,
    User,
    File,
    AppUser
)

admin.site.register(AppUser)
admin.site.register(Role)
admin.site.register(File)
admin.site.register(User)
