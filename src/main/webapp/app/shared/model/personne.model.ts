import { IDon } from 'app/shared/model//don.model';
import { IActivite } from 'app/shared/model//activite.model';
import { IPetition } from 'app/shared/model//petition.model';

export interface IPersonne {
    id?: number;
    idPersonne?: number;
    nom?: string;
    prenom?: string;
    dons?: IDon[];
    activites?: IActivite[];
    petitions?: IPetition[];
}

export class Personne implements IPersonne {
    constructor(
        public id?: number,
        public idPersonne?: number,
        public nom?: string,
        public prenom?: string,
        public dons?: IDon[],
        public activites?: IActivite[],
        public petitions?: IPetition[]
    ) {}
}
