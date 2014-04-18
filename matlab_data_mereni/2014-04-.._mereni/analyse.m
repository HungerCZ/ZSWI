function analyse(data, offset, size)

    prahovaHodnotaMrkani = 400;
    ano = 0;
	korektni = 0;
	tolerance = 20; % tolerance v procentech

    for i=(offset):(offset+size) 
        if(data(1,i+1) > prahovaHodnotaMrkani)
            ano = ano + 1;
            if(data(1,i+1) > prahovaHodnotaMrkani)
                korektni = korektni + 1;
            end
        end
    end

    procentSpravne = (korektni * 100)/ano;

    if(procentSpravne > (100 - tolerance))
        fprintf('%d mrknuto\n', i);
    end
