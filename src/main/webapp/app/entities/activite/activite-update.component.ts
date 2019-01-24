import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IActivite } from 'app/shared/model/activite.model';
import { ActiviteService } from './activite.service';
import { IPersonne } from 'app/shared/model/personne.model';
import { PersonneService } from 'app/entities/personne';

@Component({
    selector: 'jhi-activite-update',
    templateUrl: './activite-update.component.html'
})
export class ActiviteUpdateComponent implements OnInit {
    activite: IActivite;
    isSaving: boolean;

    personnes: IPersonne[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected activiteService: ActiviteService,
        protected personneService: PersonneService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ activite }) => {
            this.activite = activite;
        });
        this.personneService.query().subscribe(
            (res: HttpResponse<IPersonne[]>) => {
                this.personnes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.activite.id !== undefined) {
            this.subscribeToSaveResponse(this.activiteService.update(this.activite));
        } else {
            this.subscribeToSaveResponse(this.activiteService.create(this.activite));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IActivite>>) {
        result.subscribe((res: HttpResponse<IActivite>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPersonneById(index: number, item: IPersonne) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
