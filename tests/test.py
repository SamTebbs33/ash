import yaml
import subprocess
import sys

result_list = []
failed_tests = 0
num_tests = 0

def do_test(name, path, run):
	print "\n\t\x1B[34m Compiling: " + name + "\x1B[00m"
	status = subprocess.call(["java", "-cp", "../build:../lib/asm-5.0.4.jar:../lib/asm-tree-5.0.4.jar", "ashc/main/AshMain", path+".ash", "-o", "../classes"])
	if run and status == 0:
		print "\n\t\x1B[34m Running: " + name + "\x1B[00m"
		status = subprocess.call(["java", "-cp", "../classes", path])
	return status

def get_symbol(test_pass):
	if test_pass == 0:
		return "+"
	else:
		return "-"

def print_tests():
	print "\n\t\x1B[34m Results:"
	print "\t\tExpect\tPass\tName"
	if result_list is not None:
		for test in result_list:
			test_name = test[0]
			test_pass = test[1]
			test_status_code = test[2]
			if test_pass != test_status_code:
				colour = "\x1B[31m"
			else:
				colour = "\x1B[32m"
			print colour+"\t\t["+get_symbol(test_pass)+"]\t["+get_symbol(test_status_code)+"]\t" + test_name + "\x1B[00m"		

file = open("../tests.yml", "r")
yml = yaml.load(file)

if yml is not None:
	for test in yml:
		test_name = test
		test_pass = 0 if yml[test]["pass"] else 1
		test_path = yml[test]["path"]
		test_run = yml[test]["run"]
		status_code = do_test(test_name, test_path, test_run)
		if status_code != test_pass:
			failed_tests += 1
		num_tests += 1
		result_list.append((test_name, test_pass, status_code))
	
print_tests()
print "\n\t" + `failed_tests` + "/" + `num_tests` + " tests failed\n"
sys.exit(0 if failed_tests == 0 else 1)