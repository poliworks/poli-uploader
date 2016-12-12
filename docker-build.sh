#!/usr/bin/env bash

echo "Starting to build application to prod..."
lein ring uberjar
echo "Building docker image..."
docker build . -t leoiacovini/poli-uploader
echo "Done building docker image!"
echo "Now pushing to remote..."
docker push leoiacovini/poli-uploader
echo "Done, image pushed to leoiacovini/poli-uploader:latest"
