#!/usr/bin/env zsh

# always ensure latest sync
sudo pacman -Syy

# =(...) creates a searchable tempfile in zsh (a bit like <(...))
# first add all lines matching to the array variable
: ${(@Af)=pacup::="$(ack '^linux' =(pacman -Qu))"}

if [[ ${#pacup} -gt 0 ]]
then
    print -P "We have found the following %F{blue}${#pacup}%f packages in upgrade list:"
    for pkg in ${pacup[@]}
    do
        local warr
        : ${(A)=warr::=${(ws: :)pkg[@]}}
        print -P "Package %F{cyan}${warr[1]}%f currently at version ${warr[2]} can be upgraded to ${warr[4]}"
        unset warr
    done

    print -P 'You should probably answer the next question with "%F{green}Yes%f" unless these do not look like Linux kernel upgrades.'
    print -P 'As a security measure, the boot partition is mounted as %F{red}read-only (ro)%f by default (/etc/fstab). This script will remount as read-write so the kernel upgrade will not fail on trying to save early bootspace (initramfs) to the partition.'
    vared -p 'Remount /boot to read-write (rw) and continue upgrade?' -c tmp

    pattern='^[Y|y].*'

    if [[ $tmp =~ $pattern ]]
    then
        print -Pn 'Remounting /boot read-write enabled...'
        sudo mount -o remount,rw /boot
        print -P '[%F{green}DONE%f]'
        print -P 'Starting with %F{yellow}full system upgrade%f of ArchLinux weee...'
        sudo pacman -Syyu
    fi
    

fi


# vim: :ft=zsh:ai:et:sw=4:sts=4:ts=4:nu:

