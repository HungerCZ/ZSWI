function mrknuto = analyse(data, offset, size, prahovaHodnotaMrkani, tolerance, click)

    global predchozi;

    ano = 0;
	korektni = 0;


    for i=(offset):(offset+size-1) 
        if(data(i+1,1) > prahovaHodnotaMrkani)
            ano = ano + 1;
            if(data(i,1) > prahovaHodnotaMrkani)
                korektni = korektni + 1;
            end
        end
    end

    procentSpravne = (korektni * 100)/ano;
    mrknuto = false;

    if(procentSpravne > (100 - tolerance))
        if(predchozi == false)
            fprintf('%s blinked\n', datestr(now, 'HH:MM:SS'));
            if(click)
                !mouse_click.exe
            end
            mrknuto = true;
        end
        predchozi = true;
    else
        predchozi = false;
    end

    
