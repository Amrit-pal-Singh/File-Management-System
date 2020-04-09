from django.contrib import admin

from .models import (
    Hierarchy,
    Files,
    UserAccount,
    Office,
    OfficerAccount,
    Position
)

admin.site.register(Hierarchy)
admin.site.register(Files)
admin.site.register(UserAccount)
admin.site.register(Office)
admin.site.register(OfficerAccount)
admin.site.register(Position)