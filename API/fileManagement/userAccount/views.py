from django.shortcuts import render
from django.shortcuts import render, redirect
from django.contrib.auth import logout
from django.shortcuts import redirect
from django.contrib.auth.forms import UserCreationForm
from userAccount.forms import RegisterForm
from restaccount.models import DriverUser


def register(request):

	if request.user.is_authenticated:
		return redirect('userAccount:logout')
	else:
		if request.method == 'POST':
			form = RegisterForm(request.POST)
			if form.is_valid():
				form.save()
				return redirect('userProfile:list')
		else:
			form = RegisterForm()
			args = {'form':form}
			return render(request, 'userAccount/register.html', args)