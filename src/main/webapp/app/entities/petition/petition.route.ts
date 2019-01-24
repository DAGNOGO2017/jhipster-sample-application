import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Petition } from 'app/shared/model/petition.model';
import { PetitionService } from './petition.service';
import { PetitionComponent } from './petition.component';
import { PetitionDetailComponent } from './petition-detail.component';
import { PetitionUpdateComponent } from './petition-update.component';
import { PetitionDeletePopupComponent } from './petition-delete-dialog.component';
import { IPetition } from 'app/shared/model/petition.model';

@Injectable({ providedIn: 'root' })
export class PetitionResolve implements Resolve<IPetition> {
    constructor(private service: PetitionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Petition> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Petition>) => response.ok),
                map((petition: HttpResponse<Petition>) => petition.body)
            );
        }
        return of(new Petition());
    }
}

export const petitionRoute: Routes = [
    {
        path: 'petition',
        component: PetitionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Petitions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'petition/:id/view',
        component: PetitionDetailComponent,
        resolve: {
            petition: PetitionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Petitions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'petition/new',
        component: PetitionUpdateComponent,
        resolve: {
            petition: PetitionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Petitions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'petition/:id/edit',
        component: PetitionUpdateComponent,
        resolve: {
            petition: PetitionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Petitions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const petitionPopupRoute: Routes = [
    {
        path: 'petition/:id/delete',
        component: PetitionDeletePopupComponent,
        resolve: {
            petition: PetitionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Petitions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
