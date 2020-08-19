from django.db import models
from django.contrib.auth.models import Group, PermissionsMixin
from django.contrib.auth.base_user import AbstractBaseUser, BaseUserManager
from django.contrib import auth
from django.contrib.auth.validators import UnicodeUsernameValidator
from django.utils import timezone
from django.core.mail import send_mail
from django.utils.translation import gettext_lazy as _
from django.conf import settings
import json


class Role(models.Model):
    '''
    Every personal is given a role.
    If there is no role then he is a simple customer
    '''
    name = models.CharField(max_length=100)
    department = models.CharField(max_length=100, blank=True)
    
    def __str__(self):
        return str(self.name) + ' ' +str(self.department)



class UserManager(BaseUserManager):
    use_in_migrations = True

    def generate_user(self, email, password, **extra_fields):
        """
        Create and save a user with the given email, and password.
        """
        if not email:
            raise ValueError('The given email must be set')
        email = self.normalize_email(email)
        user = self.model(email=email, **extra_fields)
        user.set_password(password)
        # user.save(using=self._db)
        user.save()
        return user

    def create_user(self, email, password=None, **extra_fields):
        extra_fields.setdefault('is_staff', False)
        extra_fields.setdefault('is_superuser', False)
        return self.generate_user(email, password, **extra_fields)

    def create_superuser(self, email, password=None, **extra_fields):
        extra_fields.setdefault('is_staff', True)
        extra_fields.setdefault('is_superuser', True)

        if extra_fields.get('is_staff') is not True:
            raise ValueError('Superuser must have is_staff=True.')
        if extra_fields.get('is_superuser') is not True:
            raise ValueError('Superuser must have is_superuser=True.')

        return self.generate_user(email, password, **extra_fields)
    def with_perm(self, perm, is_active=True, include_superusers=True, backend=None, obj=None):
        if backend is None:
            backends = auth._get_backends(return_tuples=True)
            if len(backends) == 1:
                backend, _ = backends[0]
            else:
                raise ValueError(
                    'You have multiple authentication backends configured and '
                    'therefore must provide the `backend` argument.'
                )
        elif not isinstance(backend, str):
            raise TypeError(
                'backend must be a dotted import path string (got %r).'
                % backend
            )
        else:
            backend = auth.load_backend(backend)
        if hasattr(backend, 'with_perm'):
            return backend.with_perm(
                perm,
                is_active=is_active,
                include_superusers=include_superusers,
                obj=obj,
            )
        return self.none()


class User(AbstractBaseUser, PermissionsMixin):
    '''
    Creating custom user
    '''

    username_validator = UnicodeUsernameValidator()
    first_name = models.CharField(max_length=30, blank=True)
    last_name = models.CharField(max_length=150, blank=True)
    email = models.EmailField(unique=True)
    is_staff = models.BooleanField(
        _('staff status'),
        default=False,
        help_text=_('Designates whether the user can log into this admin site.'),
    )
    is_active = models.BooleanField(
        _('active'),
        default=True,
        help_text=_(
            'Designates whether this user should be treated as active. '
            'Unselect this instead of deleting accounts.'
        ),
    )
    objects = UserManager()
    date_joined = models.DateTimeField(_('date joined'), default=timezone.now)
    EMAIL_FIELD = 'email'
    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = []

    class Meta:
        verbose_name = _('user')
        verbose_name_plural = _('users')

    def clean(self):
        super().clean()
        self.email = self.__class__.objects.normalize_email(self.email)

    def get_full_name(self):
        full_name = '%s %s' % (self.first_name, self.last_name)
        return full_name.strip()

    def get_first_name(self):
        return self.first_name
    
    # def email_user(self, subject, message, from_email=None, **kwargs):
    #     """Send an email to this user."""
    #     send_mail(subject, message, from_email, [self.email], **kwargs)


class AppUser(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    roles = models.ManyToManyField(Role, blank=True)
    
    def __str__(self):
        return str(self.user.first_name) + " " + str(self.user.last_name)

class File(models.Model):
    '''
    For every file this model will be used.
    '''
    qr = models.CharField(max_length=1000, unique=True)
    name = models.CharField(max_length=500)
    user = models.ForeignKey(AppUser, on_delete=models.CASCADE)
    time_generated = models.DateTimeField(auto_now_add=True)
    restarted = models.BooleanField()
    path = models.CharField(max_length=1000, null=True)
    plan_to_send_time = models.CharField(max_length=100, blank=True, null=True)
    plan_to_send = models.ForeignKey(Role, on_delete=models.CASCADE,null=True)
    plan_to_send_generator = models.ForeignKey(User, on_delete=models.CASCADE, null=True, related_name='plan_to_send_generator')
    approved = models.CharField(max_length=1000, default='')
    disapproved = models.CharField(max_length=1000, default='')

    def save(self, *args, **kwargs):
        print(self.path)
        return super().save(*args, **kwargs)

    def __str__(self):
        return str(self.qr)

    def set_path(self, x):
        return json.dumps(x)

    def get_path(self):
        return json.loads(self.path)


    def set_approved(self, x):
        self.approved = json.dumps(x)
    
    def get_approved(self):
        return json.loads(self.approved)

    def add_approved(self):
        pass

    def add_path(self):
        pass
    