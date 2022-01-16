scan:
ifdef dir
	cp -r ${dir}/* ./public/
else
	echo "Param 'dir' not defined. Test mode is enabled."
	cp -r ./test/public/* ./public/
endif
	docker-compose up
