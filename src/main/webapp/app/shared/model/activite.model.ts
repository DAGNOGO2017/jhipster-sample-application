import { IPersonne } from 'app/shared/model//personne.model';

export interface IActivite {
    id?: number;
    idActivite?: number;
    nomActivite?: string;
    personnes?: IPersonne[];
}

export class Activite implements IActivite {
    constructor(public id?: number, public idActivite?: number, public nomActivite?: string, public personnes?: IPersonne[]) {}
}
