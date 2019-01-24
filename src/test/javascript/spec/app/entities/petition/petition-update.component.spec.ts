/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PetitionUpdateComponent } from 'app/entities/petition/petition-update.component';
import { PetitionService } from 'app/entities/petition/petition.service';
import { Petition } from 'app/shared/model/petition.model';

describe('Component Tests', () => {
    describe('Petition Management Update Component', () => {
        let comp: PetitionUpdateComponent;
        let fixture: ComponentFixture<PetitionUpdateComponent>;
        let service: PetitionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [PetitionUpdateComponent]
            })
                .overrideTemplate(PetitionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PetitionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PetitionService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Petition(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.petition = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Petition();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.petition = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
