import os
import glob
import matplotlib.pyplot as plt
import matplotlib.backends.backend_pdf
import numpy as np

filepaths = sorted(glob.glob(os.getcwd() + '/*'))
filepaths = [filepath for filepath in filepaths if filepath.endswith('.txt')]
filenames = [os.path.basename(filepath) for filepath in filepaths]
iterations = [filename.split('i')[1][0] for filename in filenames]


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
				value = ''.join([v for v in value if v.isnumeric() or v == '.'])
				value = float(value)
				scores.append(value)
	scores = np.array(scores)
	avg_score = scores.average()
	return avg_score


iter_paths = dict()
for i in range(0, len(filepaths)):
	if iterations[i] not in iter_paths:
		iter_paths[iterations[i]] = [filepaths[i]]
	else:
		iter_paths[iterations[i]].append(filepaths[i])


for iteration in iter_paths.keys():
	datasets = get_datasets_of_filepaths(iter_paths[iteration])
	avg_score = get_scores(datasets) 
	filename = os.path.basename(iter_paths[iteration][0])
	print("{}: {}".format(filename, avg_score))

