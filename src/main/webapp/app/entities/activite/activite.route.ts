import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Activite } from 'app/shared/model/activite.model';
import { ActiviteService } from './activite.service';
import { ActiviteComponent } from './activite.component';
import { ActiviteDetailComponent } from './activite-detail.component';
import { ActiviteUpdateComponent } from './activite-update.component';
import { ActiviteDeletePopupComponent } from './activite-delete-dialog.component';
import { IActivite } from 'app/shared/model/activite.model';

@Injectable({ providedIn: 'root' })
export class ActiviteResolve implements Resolve<IActivite> {
    constructor(private service: ActiviteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Activite> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Activite>) => response.ok),
                map((activite: HttpResponse<Activite>) => activite.body)
            );
        }
        return of(new Activite());
    }
}

export const activiteRoute: Routes = [
    {
        path: 'activite',
        component: ActiviteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Activites'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'activite/:id/view',
        component: ActiviteDetailComponent,
        resolve: {
            activite: ActiviteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Activites'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'activite/new',
        component: ActiviteUpdateComponent,
        resolve: {
            activite: ActiviteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Activites'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'activite/:id/edit',
        component: ActiviteUpdateComponent,
        resolve: {
            activite: ActiviteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Activites'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const activitePopupRoute: Routes = [
    {
        path: 'activite/:id/delete',
        component: ActiviteDeletePopupComponent,
        resolve: {
            activite: ActiviteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Activites'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
