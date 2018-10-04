import os
import glob
import matplotlib.pyplot as plt
import matplotlib.backends.backend_pdf

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

def __plot_single_variable__(variable_name, values, filename):
    fig, ax = plt.subplots(figsize=(20, 11))
    iterations = range(0,len(values))
    ax.set_title('{}'.format(filename), fontsize='large')
    ax.set_xlabel("iterations")
    ax.set_ylabel('{}'.format(variable_name))
    ax.plot(iterations, values)
    return fig

def plot_datasets(dicts_datasets):
	index = 0
	pdf = matplotlib.backends.backend_pdf.PdfPages("plotted_variables_per_run.pdf")
	for dict_dataset in dicts_datasets:
	    filename = filenames[index]
	    for variable_name in dict_dataset.keys():
	        if (variable_name != "Score") and (variable_name != "Runtime"):
	            fig = __plot_single_variable__(variable_name, dict_dataset[variable_name], filename)
	            pdf.savefig(fig)
	    index += 1
	pdf.close()


datasets = get_datasets_of_filepaths(filepaths)
dicts_datasets = get_dicts_datasets(datasets)
plot_datasets(dicts_datasets)











