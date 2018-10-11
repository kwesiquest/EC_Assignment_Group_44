import os
import glob
import matplotlib.pyplot as plt
import matplotlib.backends.backend_pdf
import numpy as np

filepaths = sorted(glob.glob(os.getcwd() + '/*'))
filepaths = [filepath for filepath in filepaths if filepath.endswith('.txt')]
filenames = [os.path.basename(filepath) for filepath in filepaths]


def get_datasets_of_filepaths(filepaths):
	datasets = []
	for filepath in filepaths:
	    with open(filepath) as txtfile:
	        data = txtfile.readlines()
	    datasets.append(data)
	return datasets

def get_data(datasets):
	dict_datasets = dict()
	score = 0
	for dataset in datasets:
		for line in dataset:
			if ":" in line and "Runtime" not in line:
				parameter_name = line.split(":")[0]
				value = line.split(":")[1]
				value = ''.join([v for v in value if v.isnumeric() or v == '.'])
				value = float(value)
				if "Score" in line:
					parameter_name = "Score"
				if parameter_name in dict_datasets:
					dict_datasets[parameter_name].append(value)
				else:
					dict_datasets[parameter_name] = [value]
	return dict_datasets


def get_dicts_per_param(dict_datasets):
	#list of dicts per parameter name
	dicts_per_param = []
	#dict
	#key = parameter value
	#value = scores
	parameter_names = list(dict_datasets.keys())
	for parameter_name in dict_datasets.keys():
		param_values = dict_datasets[parameter_name]
		dict_param = {}
		for param_value in param_values:
			if param_value not in dict_param:
				dict_param[param_value] = []
		for dataset in datasets:
			parameter_value = None
			score = None
			for line in dataset:
				if parameter_name in line:
					param_value = line.split(":")[1]
					param_value = ''.join([v for v in param_value if v.isnumeric() or v == '.'])
					param_value = float(param_value)
				if "Score" in line:
					score = line.split(":")[1]
					score = ''.join([v for v in score if v.isnumeric() or v == '.'])
					score = float(score)
			if score != None and param_value != None:
				dict_param[param_value].append(score)
		dicts_per_param.append(dict_param)
	return dicts_per_param, parameter_names


def __plot_single_param__(parameter_name, parameter_values, average_scores):
	fig, ax = plt.subplots(figsize=(20, 11))
	parameter = parameter_values
	ax.set_title('{}'.format(parameter_name), fontsize='large')
	ax.set_xlabel('{}'.format(parameter_name))
	ax.set_ylabel('score')
	ax.plot(parameter_values, average_scores)
	return fig


def plot_parameters(dicts_per_param, parameter_names):
	pdf = matplotlib.backends.backend_pdf.PdfPages("plotted_parameters_over_all_runs.pdf")
	index = -1
	
	parameter_avg_scores = []
	for dict_param in dicts_per_param:
		index += 1
		parameter_name = parameter_names[index]
		parameter_values = dict_param.keys()
		avg_scores = []
		for value in parameter_values:
			scores = list(dict_param.get(value))
			scores = np.array(scores)
			avg_score = np.average(scores)
			avg_scores.append(avg_score)
		fig = __plot_single_param__(parameter_name, parameter_values, avg_scores)
		pdf.savefig(fig)
	pdf.close()


#dict
#keys = parameter names
#values = possible parameter values

datasets = get_datasets_of_filepaths(filepaths)
dict_datasets = get_data(datasets)


#list of dicts per parameter name
#dict
#key = parameter value
#value = scores
dicts_per_param, parameter_names = get_dicts_per_param(dict_datasets)

plot_parameters(dicts_per_param, parameter_names)
