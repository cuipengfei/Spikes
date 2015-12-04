# -*- coding: utf-8 -*-
# Data preparation
from __future__ import print_function

from sqlalchemy import create_engine
from cubes.tutorial.sql import create_table_from_csv

# 1. Prepare SQL data in memory

print("preparing data...")

engine = create_engine('sqlite:///personal_finance.sqlite')

create_table_from_csv(engine,
                      "personal_finance.csv",
                      table_name="personal_finance",
                      fields=[
                            ("type", "string"),
                            ("amount", "integer"),
                            ("time", "integer")],
                      create_id=True
                  )

print("csv to sqlite, ok")
