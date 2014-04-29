function mrknuto = analyse(data, offset, size, prahovaHodnotaMrkan, mouseClick)

    global predchozi;

    %prahovaHodnotaMrkani = 500;
    ano = 0;
	korektni = 0;
	tolerance = 20; % tolerance v procentech
    
    

    for i=(offset):(offset+size-1) 
        if(data(i+2,1) > prahovaHodnotaMrkani)
            ano = ano + 1;
            if(data(i+1,1) > prahovaHodnotaMrkani)
                korektni = korektni + 1;
            end
        end
    end

    procentSpravne = (korektni * 100)/ano;
    mrknuto = false;

    if(procentSpravne > (100 - tolerance))
        if(predchozi == false)
            fprintf('%s mrknuto\n', datestr(now, 'HH:MM:SS'));
            
            if(mouseClick)
                !mouse_click.exe
            end
            
            mrknuto = true;
        end
        predchozi = true;
    else
        predchozi = false;
    end

    
