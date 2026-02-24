--1.nume+prenume client impreuna cu marca si modelul masiniilor pe care le detine(select cu join)
select c.nume, c.prenume, m.marca, m.model
from clienti c
join masini m on c.id_client=m.id_client

--2. afiseaza fiecare vanzare cu:nume cleint, marca+model masina si nume angajat care a realizat vanzarea(select cu join 3 tabele)
select c.nume, c.prenume, m.marca, m.model, a.nume, a.prenume
from clienti c
join masini m on c.id_client=m.id_client
join vanzari v on m.id_masina=v.id_masina
join angajati a on v.id_angajat=a.id_angajat

--3. numara cate masini are fiecare client(select cu group by)
select c.nume, c.prenume, count(m.id_masina) as nr_masini from clienti c
left join masini m on c.id_client=m.id_client
group by c.id_client, c.nume, c.prenume

--4.Afiseaza pretul total al vanzarilor realizate de fiecare angajat(select cu functii agregare: sum)
select sum(v.pret_final) as pret_total, a.nume, a.prenume
from angajati a
join vanzari v on v.id_angajat=a.id_angajat
group by a.nume, a.prenume

--5. Gaseste cleintii care au cumparat masini cu pretul mai mare decat media preturilor tutror masinilor(select cu subinterogare)
select c.nume, c.prenume from clienti c
join vanzari v on c.id_client=v.id_client
where v.pret_final>(SELECT AVG(pret_final) FROM vanzari)

--6. Actualizeaza salariul angajatilor de tip Vanzator crescandu-l cu 10%(update)
update angajati
set salariu=salariu*1.1
where functie='Vanzator'

--7. Sterge toate reviziilr pt masina cu id_masina=5(delete)
delete from revizii
where id_masina=5

--8. Afiseaza toate masinile impreuna cu optiunile lor(select cu n-n join)
select m.marca, m.model, o.nume_optiune
from masini m
join masina_optiune mo on m.id_masina=mo.id_masina
join optiuni o on mo.id_optiune=o.id_optiune

--9. Afiseaza angajatii care au vandut mai mult decat media vanzarilor tuturor angajatilor(subinterogare)
select a.nume, a.prenume from angajati a
join vanzari v on a.id_angajat=v.id_angajat
group by a.nume, a.prenume
having sum(v.pret_final)> (select avg(pret_final) from vanzari)

--10. AFiseaza toate reviziile efectuate de mecanici pt masinile clientilor care au BMW/Audi(joinuri multiple)
SELECT r.id_revizie, m.marca, m.model, r.data_revizie, r.cost, r.descriere, a.nume, a.prenume
FROM revizii r
JOIN masini m ON r.id_masina = m.id_masina
JOIN vanzari v ON m.id_masina = v.id_masina
JOIN angajati a ON v.id_angajat = a.id_angajat
WHERE a.functie = 'Mecanic' AND m.marca IN ('BMW','Audi');
