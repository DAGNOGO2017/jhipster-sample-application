import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Don } from 'app/shared/model/don.model';
import { DonService } from './don.service';
import { DonComponent } from './don.component';
import { DonDetailComponent } from './don-detail.component';
import { DonUpdateComponent } from './don-update.component';
import { DonDeletePopupComponent } from './don-delete-dialog.component';
import { IDon } from 'app/shared/model/don.model';

@Injectable({ providedIn: 'root' })
export class DonResolve implements Resolve<IDon> {
    constructor(private service: DonService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Don> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Don>) => response.ok),
                map((don: HttpResponse<Don>) => don.body)
            );
        }
        return of(new Don());
    }
}

export const donRoute: Routes = [
    {
        path: 'don',
        component: DonComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Dons'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'don/:id/view',
        component: DonDetailComponent,
        resolve: {
            don: DonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dons'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'don/new',
        component: DonUpdateComponent,
        resolve: {
            don: DonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dons'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'don/:id/edit',
        component: DonUpdateComponent,
        resolve: {
            don: DonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dons'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const donPopupRoute: Routes = [
    {
        path: 'don/:id/delete',
        component: DonDeletePopupComponent,
        resolve: {
            don: DonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dons'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
