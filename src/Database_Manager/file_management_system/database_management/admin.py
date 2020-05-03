from django.contrib import admin

from .models import (
    Role,
    User,
    File,
)

admin.site.register(Role)
admin.site.register(File)
admin.site.register(User)
