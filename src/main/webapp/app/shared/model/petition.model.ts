import { IPersonne } from 'app/shared/model//personne.model';

export interface IPetition {
    id?: number;
    idPetition?: number;
    libelepetition?: string;
    personnes?: IPersonne[];
}

export class Petition implements IPetition {
    constructor(public id?: number, public idPetition?: number, public libelepetition?: string, public personnes?: IPersonne[]) {}
}
