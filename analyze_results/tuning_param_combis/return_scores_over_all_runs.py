import os
import glob
import matplotlib.pyplot as plt
import matplotlib.backends.backend_pdf
import numpy as np



def get_iteration(filepath):
	filename = os.path.basename(filepath)
	iteration = filename.split('i')[1].split('_')[0]
	print(iteration)
	return int(iteration)

def get_datasets_of_filepaths(filepaths):
	datasets = []
	for filepath in filepaths:
	    with open(filepath) as txtfile:
	        data = txtfile.readlines()
	    datasets.append(data)
	return datasets

def get_scores(datasets):
	scores = []
	for dataset in datasets:
		for line in dataset:
			if "Score" in line:
				value = line.split(":")[1]
				if '-' in value:
					value = 0
				else:	
					value = ''.join([v for v in value if v.isnumeric() or v == '.'])
					value = float(value)
				scores.append(value)
	scores = np.array(scores)
	avg_score = np.average(scores)
	return scores, avg_score

def get_parameter_values(filename):
	cc = int(filename.split('cc')[1].split('_')[0])/10
	sc = int(filename.split('sc')[1].split('_')[0])/10
	p = int(filename.split('p')[1].split('.')[0])
	iw = 0.4
	return sc, cc, p, iw


def print_and_write(dict_iter, n_iterations):
	cwd = os.getcwd()
	one_folder_up = '/'.join(cwd.split('/')[:-1])
	os.chdir(one_folder_up)
	with open('results_param_combi_tuning.txt', 'w') as f:
		for iteration in dict_iter.keys():
			datasets = get_datasets_of_filepaths(dict_iter[iteration])
			scores, avg_score = get_scores(datasets) 
			filename = os.path.basename(dict_iter[iteration][0])
			sc, cc, p, iw = get_parameter_values(filename)
			resulting_string = "Param values: [inertia weight={}, social constant={}, cognitive constant={}, popsize={}]. Average score of {} iterations = {}.\n".format(iw, sc, cc, p, n_iterations, avg_score)
			f.write(resulting_string)
			print(resulting_string)

def get_data_param_combi_iterations(n_param_combinations, iterations, filepaths):
	dict_iter = {}
	for i in range(1, len(iterations)+1):
		print(i)
		for param_combi_i in range(1, n_param_combinations+1):
			if i not in dict_iter:
				dict_iter[i] = [filepaths[i*param_combi_i-1]]
			else:
				dict_iter[i].append(filepaths[i*param_combi_i-1])
	return dict_iter



filepaths = glob.glob(os.getcwd() + '/*')
filepaths = [filepath for filepath in filepaths if filepath.endswith('.txt')]
filepaths = sorted(filepaths, key = lambda x: get_iteration(x))

iterations = set([get_iteration(filepath) for filepath in filepaths])
iterations = sorted(list(iterations))

n_param_combinations = 18
n_iterations = len(iterations)

dict_iter = get_data_param_combi_iterations(n_param_combinations, iterations, filepaths)

print_and_write(dict_iter, n_iterations)

