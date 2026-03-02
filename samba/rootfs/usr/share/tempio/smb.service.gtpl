# /etc/mdns.d/smb.service -- mDNS-SD advertisement of SMB service
name {{ env "HOSTNAME" }}
type _smb._tcp
port {{ index .ports "445/tcp" | default 445 }}
txt s=samba
txt v={{ .sambaversion }}
# target ha.local
# cname smb.ha.local
txt adVF=0x82
txt adVN=Home Assistant
{{ range $i, $share := .enabled_shares -}}
txt dk{{ $i }}={{ $share }}
{{ end -}}