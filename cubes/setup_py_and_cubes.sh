echo "setting up"
virtualenv --python=python3 localpy
source localpy/bin/activate
pip install -r dependencies.txt
