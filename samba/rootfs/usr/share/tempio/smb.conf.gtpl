[global]
   netbios name = {{ env "HOSTNAME" }}
   dns hostname = {{ env "HOSTNAME" }}.local
   additional dns hostnames = {{ env "HOSTNAME" }}._smb._tcp.local
   workgroup = {{ .workgroup }}
   server string = Samba Home Assistant
   local master = {{ .local_master | ternary "yes" "no" }}
   preferred master = {{ .local_master | ternary "yes" "auto" }}
   server role = standalone
   server services = smb
   smb ports = {{ index .ports "445/tcp" | default 445 }} {{ index .ports "139/tcp" | default 139 }}

   security = user
   idmap config * : backend = tdb
   idmap config * : range = 1000000-2000000

   load printers = no
   disable spoolss = yes
   {{ if .netbios }}
   disable netbios = no
   server services = +nbt
   {{ else }}
   disable netbios = yes
   {{ end }}
   dns proxy = no
   
   log level = 1

   bind interfaces only = yes
   interfaces = lo {{ .interfaces | join " " }}
   hosts allow = 127.0.0.1 {{ .allow_hosts | join " " }}

   {{ if .compatibility_mode }}
   client min protocol = NT1
   server min protocol = NT1
   lanman auth = yes
   ntlm auth = yes
   {{ else }}
   ntlm auth = no
   {{ end }}

   mangled names = no
   dos charset = CP850
   unix charset = UTF-8
   
   {{ if .apple_compatibility_mode }}
   vfs objects = catia fruit streams_xattr
   {{ end }}

   server signing = {{ .server_signing }}
   allow dns updates = disabled

{{ if (has "config" .enabled_shares) }}
[config]
   browseable = yes
   writeable = yes
   path = /smbshare/homeassistant

   valid users = {{ .username }}
   force user = root
   force group = root
   veto files = /{{ .veto_files | join "/" }}/
   delete veto files = {{ eq (len .veto_files) 0 | ternary "no" "yes" }}
{{ end }}

{{ if (has "addons" .enabled_shares) }}
[addons]
   browseable = yes
   writeable = yes
   path = /smbshare/addons

   valid users = {{ .username }}
   force user = root
   force group = root
   veto files = /{{ .veto_files | join "/" }}/
   delete veto files = {{ eq (len .veto_files) 0 | ternary "no" "yes" }}
{{ end }}

{{ if (has "addon_configs" .enabled_shares) }}
[addon_configs]
   browseable = yes
   writeable = yes
   path = /smbshare/addon_configs

   valid users = {{ .username }}
   force user = root
   force group = root
   veto files = /{{ .veto_files | join "/" }}/
   delete veto files = {{ eq (len .veto_files) 0 | ternary "no" "yes" }}
{{ end }}

{{ if (has "ssl" .enabled_shares) }}
[ssl]
   browseable = yes
   writeable = yes
   path = /smbshare/ssl

   valid users = {{ .username }}
   force user = root
   force group = root
   veto files = /{{ .veto_files | join "/" }}/
   delete veto files = {{ eq (len .veto_files) 0 | ternary "no" "yes" }}
{{ end }}

{{ if (has "share" .enabled_shares) }}
[share]
   browseable = yes
   writeable = yes
   path = /smbshare/share

   valid users = {{ .username }}
   force user = root
   force group = root
   veto files = /{{ .veto_files | join "/" }}/
   delete veto files = {{ eq (len .veto_files) 0 | ternary "no" "yes" }}
{{ end }}

{{ if (has "backup" .enabled_shares) }}
[backup]
   browseable = yes
   writeable = yes
   path = /smbshare/backup

   valid users = {{ .username }}
   force user = root
   force group = root
   veto files = /{{ .veto_files | join "/" }}/
   delete veto files = {{ eq (len .veto_files) 0 | ternary "no" "yes" }}
{{ end }}

{{ if (has "media" .enabled_shares) }}
[media]
   browseable = yes
   writeable = yes
   path = /smbshare/media

   valid users = {{ .username }}
   force user = root
   force group = root
   veto files = /{{ .veto_files | join "/" }}/
   delete veto files = {{ eq (len .veto_files) 0 | ternary "no" "yes" }}
{{ end }}
