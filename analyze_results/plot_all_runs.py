import os
import glob
import matplotlib.pyplot as plt
import matplotlib.backends.backend_pdf
import numpy as np

filepaths = glob.glob(os.getcwd() + '/*')
filepaths = [filepath for filepath in filepaths if filepath.endswith('.txt')]
filenames = [os.path.basename(filepath) for filepath in filepaths]

def get_datasets_of_filepaths(filepaths):
	datasets = []
	for filepath in filepaths:
	    with open(filepath) as txtfile:
	        data = txtfile.readlines()
	    datasets.append(data)
	return datasets

def __get_dict_data__(data):
    dict_data = dict()
    for line in data:
        if ':' in line:
            if '\n' in line:
                line = line.replace('\n', '')
            variable_name = line.split(':')[0]
            value = line.split(':')[1]
            value = ''.join([v for v in value if v.isnumeric() or v == '.'])
            try:
                value = float(value)
            except:
                print(line)
            if variable_name in dict_data:
                dict_data[variable_name].append(value)
            else:
                dict_data[variable_name] = [value]
        else:
            pass
    return dict_data
 

def get_dicts_datasets(datasets):
	dicts_datasets = []
	for data in datasets:
	    dict_data = __get_dict_data__(data)
	    dicts_datasets.append(dict_data)
	return dicts_datasets

def get_dict_merged_variables_over_runs(dicts_datasets):
    dict_all_runs = dict()
    for dict_dataset in dicts_datasets:
        for variable_name in dict_dataset.keys():
            values = dict_dataset[variable_name]
            if variable_name in dict_all_runs:
                dict_all_runs[variable_name].append(values)
            else:
                dict_all_runs[variable_name] = [values]
    return dict_all_runs

def get_dict_means_and_stds_per_run(dict_all_runs):
    dict_means_stds = dict()
    for variable_name in dict_all_runs.keys():
        values_runs = dict_all_runs[variable_name]
        nr_of_runs = len(values_runs)
        nr_of_iterations = len(values_runs[0])
        mean_iterations = []
        std_iterations = []

        for iteration in range(0, nr_of_iterations):
            values_iteration_list = []

            for values_run in values_runs:
                values_iteration_list.append(values_run[iteration])

            values_iteration = np.array(values_iteration_list)   
            mean_iteration = values_iteration.mean()
            std_iteration = values_iteration.std()

            mean_iterations.append(mean_iteration)
            std_iterations.append(std_iteration)
        dict_means_stds[variable_name] = [mean_iterations, std_iterations]
    return dict_means_stds

def __plot_variable_with_errorbar__(variable_name, values, plotname="unnamed"):
    fig, ax = plt.subplots(figsize=(20, 11))
    iterations = range(0,len(values[0]))
    ax.set_title('{}'.format(plotname), fontsize='large')
    ax.set_xlabel("generations")
    ax.set_ylabel('{}'.format(variable_name))
    means = values[0]
    stds = values[1]
    ax.errorbar(iterations, means, yerr = stds, fmt = 'o', markersize=2, capsize=4)
    return fig

def plot_datasets_all_runs(dict_means_stds, plotname="unnamed"):
    pdf = matplotlib.backends.backend_pdf.PdfPages("average_performance_over_all_runs.pdf")
    for variable_name in dict_means_stds.keys():
        if (variable_name != "Score") and (variable_name != "Runtime"):
            fig = __plot_variable_with_errorbar__(variable_name, dict_means_stds[variable_name], plotname)
            pdf.savefig(fig)
    pdf.close()

datasets = get_datasets_of_filepaths(filepaths)
dicts_datasets = get_dicts_datasets(datasets)
dict_all_runs = get_dict_merged_variables_over_runs(dicts_datasets)
dict_means_stds = get_dict_means_and_stds_per_run(dict_all_runs)
plot_datasets_all_runs(dict_means_stds)