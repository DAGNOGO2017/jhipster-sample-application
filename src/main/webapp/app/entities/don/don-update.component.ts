import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDon } from 'app/shared/model/don.model';
import { DonService } from './don.service';
import { IPersonne } from 'app/shared/model/personne.model';
import { PersonneService } from 'app/entities/personne';

@Component({
    selector: 'jhi-don-update',
    templateUrl: './don-update.component.html'
})
export class DonUpdateComponent implements OnInit {
    don: IDon;
    isSaving: boolean;

    personnes: IPersonne[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected donService: DonService,
        protected personneService: PersonneService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ don }) => {
            this.don = don;
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
        if (this.don.id !== undefined) {
            this.subscribeToSaveResponse(this.donService.update(this.don));
        } else {
            this.subscribeToSaveResponse(this.donService.create(this.don));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDon>>) {
        result.subscribe((res: HttpResponse<IDon>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
