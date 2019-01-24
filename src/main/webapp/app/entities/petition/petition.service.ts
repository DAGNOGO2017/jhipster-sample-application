import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPetition } from 'app/shared/model/petition.model';

type EntityResponseType = HttpResponse<IPetition>;
type EntityArrayResponseType = HttpResponse<IPetition[]>;

@Injectable({ providedIn: 'root' })
export class PetitionService {
    public resourceUrl = SERVER_API_URL + 'api/petitions';

    constructor(protected http: HttpClient) {}

    create(petition: IPetition): Observable<EntityResponseType> {
        return this.http.post<IPetition>(this.resourceUrl, petition, { observe: 'response' });
    }

    update(petition: IPetition): Observable<EntityResponseType> {
        return this.http.put<IPetition>(this.resourceUrl, petition, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPetition>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPetition[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
