from django.shortcuts import render, redirect
from .forms import ListForm, UpdateForm
from django.shortcuts import render
from .models import filesModel
from django.core.paginator import Paginator

from django.shortcuts import render, get_object_or_404
from django.contrib.auth.decorators import login_required
from .viewsHelper import get_levels, set_status, set_path
from fileManagement.writeLog import saveLog

def add_files(request):
	name = request.user.username
	initial_dict = { 
        'username' : str(name), 
        'status' : "Customer", 
        'path':"Customer",
		'Office': 'asfaasda',
    } 
	form = ListForm(request.POST)
	# form = ListForm(request.POST, username=name)
	# print(form)
	if request.method == 'POST':
		saveLog(form.errors)
		print(form)
		if(form.is_valid()):
			if not request.POST._mutable:
				request.POST._mutable = True
			# request.POST = request.POST.copy()
			form = ListForm(request.POST)
			request.POST['username'] = name
			print(name, '############', form.is_valid())
			file = form.save(commit=False)
			form.save()	
			return redirect('files:list')
	context = {'post': form}
	return render(request, 'files/add.html', context)



def all_files(request):
	user = request.user.id
	form = ListForm(request.POST)
	# files = files.objects.filter(username=user).order_by('-id')
	# category_search = Category.objects.all()
	# if request.method == 'POST':
		# category_id = request.POST.get('category_id_f')
	files = filesModel.objects.all()
	
	paginator = Paginator(files, 10)
	page = request.GET.get('page')
	files = paginator.get_page(page)

	context_posts = {
		'posts': files,
		# 'category_search': '',
	}

	return render(request, 'files/all_posts.html', context_posts)


def update_file(request, pk):
	file = filesModel.objects.get(qr=pk)
	form = UpdateForm(request.POST, instance=file)
	# all_category = Category.objects.all()
	if request.method == 'POST':
		print(form.errors)
		if form.is_valid():
			form.save()
			return redirect('files:list')
	return render(request, 'files/update.html', {'form': form, 'post': file})




def delete_file(request, pk):
	file = get_object_or_404(filesModel,qr=pk)
	if request.method == 'POST':
		file.delete()
		return redirect('files:list')
	context = {
		"object": file
	}
	return render(request, 'files/delete.html', context)