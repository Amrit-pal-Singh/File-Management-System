from django.shortcuts import render, redirect
from django.shortcuts import render

from .forms import AddForm
from .forms import ListForm
from hierarchy.models import hierarchyModel, Office

from django.shortcuts import render, get_object_or_404
from django.contrib.auth.decorators import login_required
from django.core.paginator import Paginator
from fileManagement.writeLog import saveLog

def add(request):
	form = AddForm(request.POST)
	office = Office.objects.all()
	if request.method == 'POST':
		saveLog(form.errors)
		print(form.errors)
		if form.is_valid():
			hierarchy_saved = form.save(commit=False)
			# post_saved.author = request.user.office
			form.save()
			
			hiers = hierarchyModel.objects.all().order_by('-office')
			context_posts = {
				'posts': hiers,
				'categoty_search': office,
			}
			return redirect('hierarchy:list')
	return render(request, 'add.html', {'form': form, 'post_category': office})


def all_hierarchy(request):
	form = ListForm(request.POST)
	hiers = hierarchyModel.objects.all().order_by('office')
	office_search = Office.objects.all()

	if request.method == 'POST':
		# from dropdown
		officeId = request.POST.get('category_id_f')
		hiers = hierarchyModel.objects.filter(office=officeId).order_by('-office')
	
	paginator = Paginator(hiers, 10)
	page = request.GET.get('page')    
	hiers = paginator.get_page(page)
	
	context_posts = {
		'posts': hiers,
		'categoty_search': office_search,
	}

	return render(request, 'all_hierarchy.html', context_posts)

# def show_hierarchy(request, pk):
# 	post = get_object_or_404(Post, pk=pk)
# 	brand = Category.objects.get(office=post.office)
# 	return render(request, 'post.html', {'post':post, 'brand':brand})


def list_office(request, pk:None):
	form = ListForm(request.POST)
	hiers = hierarchyModel.objects.filter(office=pk).order_by('-office')
	office_search = Office.objects.all()

	if request.method == 'POST':
		office_id = request.POST.get('brand_id_f')
		hiers = hierarchyModel.objects.filter(office=office_id).order_by('-office')
	
	paginator = Paginator(hiers, 10)
	page = request.GET.get('page')
    
	hiers = paginator.get_page(page)

	context_posts = {
		'posts': hiers,
		'categoty_search': office_search,
	}

	return render(request, 'all_hierarchy.html', context_posts)