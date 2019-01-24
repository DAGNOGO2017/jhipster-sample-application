import { IPersonne } from 'app/shared/model//personne.model';

export interface IDon {
    id?: number;
    idDon?: number;
    natureDon?: string;
    personne?: IPersonne;
}

export class Don implements IDon {
    constructor(public id?: number, public idDon?: number, public natureDon?: string, public personne?: IPersonne) {}
}
