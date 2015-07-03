import yaml
import subprocess
import sys

file = open("tests.yml", "r")
yml = yaml.load(file)
result_list = []
failed_tests = 0
num_tests = 0

def do_test(name):
	print "Running test: " + name + "\n"
	return subprocess.call(["java", "-cp build", "ashc/main/AshMain", name+".ash"])
	
def get_symbol(test_pass):
	if test_pass == 1:
		return "+"
	else:
		return "-"
	
def print_tests():
	for test in result_list:
		test_name = test[0]
		test_pass = test[1]
		test_status_code = test[2]
		if test_pass != test_status_code:
			colour = "\033[91m"
		else:
			colour = "\033[92m"
		print colour+"\t["+get_symbol(test_pass)+"]\t["+get_symbol(test_status_code)+"]\t" + test_name + "\033[0m"		

for test in yml:
	test_name = test
	test_pass = yml[test]["pass"]
	status_code = do_test(test_name)
	if status_code != test_pass:
		failed_tests += 1
	num_tests += 1
	result_list.append((test_name, test_pass, status_code))
	
print_tests()
print "\n" + `failed_tests` + "/" + `num_tests` + " tests failed\n"
sys.exit(0 if failed_tests == 0 else 1)