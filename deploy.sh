gradle clean build
scp build/libs/livetrack.war 51434aab5973ca81b40004d2@livetrack-anzix.rhcloud.com:/var/lib/openshift/51434aab5973ca81b40004d2/app-root/runtime/repo/webapps/ROOT.war
rhc app restart livetrack
