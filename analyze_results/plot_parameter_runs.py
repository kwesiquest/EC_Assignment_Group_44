import os
import glob
import matplotlib.pyplot as plt
import matplotlib.backends.backend_pdf
import numpy as np

filepaths = sorted(glob.glob(os.getcwd() + '/*'))
filepaths = [filepath for filepath in filepaths if filepath.endswith('.txt')]
filenames = [os.path.basename(filepath) for filepath in filepaths]

def plot_datasets(dict_datasets):
	pdf = matplotlib.backends.backend_pdf.PdfPages("plotted_parameters_per_run.pdf")
	for parameter_name in dict_datasets.keys():
		if (parameter_name != "Score") and (parameter_name != "Runtime"):
			fig = __plot_single_variable__(parameter_name, dict_datasets[parameter_name], dict_datasets["Score"])
			pdf.savefig(fig)
	pdf.close()

def get_data(datasets):
	dict_datasets = dict()
	score = 0
	for dataset in datasets:
		for line in dataset:
			if ":" in line:
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


def __plot_single_variable__(parameter_name, parameter_values, scores):
	print(parameter_values)
	print(len(scores))
	fig, ax = plt.subplots(figsize=(20, 11))
	parameter = parameter_values
	ax.set_title('{}'.format(parameter_name), fontsize='large')
	ax.set_xlabel('{}'.format(parameter_name))
	ax.set_ylabel('score')
	ax.plot(parameter_values, scores)
	return fig


def get_datasets_of_filepaths(filepaths):
	datasets = []
	for filepath in filepaths:
	    with open(filepath) as txtfile:
	        data = txtfile.readlines()
	    datasets.append(data)
	return datasets


datasets = get_datasets_of_filepaths(filepaths)
dict_datasets = get_data(datasets)
plot_datasets(dict_datasets)
