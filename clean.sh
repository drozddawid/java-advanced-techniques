#!/bin/bash
rm -rf build
for proj in billboard client manager; do
	rm -rf "$proj/target"
done;